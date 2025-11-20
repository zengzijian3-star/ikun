package com.kagof.intellij.plugins.pokeprogress;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PokemonProgressImageDialog extends DialogWrapper {
    private final String imagePath1;
    private final String imagePath2;

    public PokemonProgressImageDialog(@Nullable final Project project, String imagePath1, String imagePath2) {
        super(project);
        this.imagePath1 = imagePath1;  // 保存第一张图片路径
        this.imagePath2 = imagePath2;  // 保存第二张图片路径
        setTitle("请 ikun 喝可乐～～");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        // 创建一个 JPanel，用于展示两张图片
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));  // 水平排列图片

        // 添加第一张图片
        panel.add(createImageComponent(imagePath1));

        // 添加第二张图片
        panel.add(createImageComponent(imagePath2));

        // 使用滚动面板，防止图片过大而无法查看
        return ScrollPaneFactory.createScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    // 创建一个显示图片的 JComponent
    private JComponent createImageComponent(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return new JLabel("No image found at the specified path.");
        }

        // 使用 ImageIcon 加载图片
        ImageIcon imageIcon = null;
        try {
            // 使用 getResource 获取类路径下的图片资源
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                imageIcon = new ImageIcon(imageUrl);  // 加载图片
            } else {
                throw new Exception("Image not found at: " + imagePath);
            }
        } catch (Exception e) {
            return new JLabel("Failed to load image. Error: " + e.getMessage());
        }

        // 获取图片并按比例缩放
        Image image = imageIcon.getImage();
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        // 设置最大宽度和高度
        int maxWidth = 400;  // 设置最大宽度
        int maxHeight = 400; // 设置最大高度

        // 计算缩放比例
        double widthRatio = (double) maxWidth / width;
        double heightRatio = (double) maxHeight / height;
        double ratio = Math.min(widthRatio, heightRatio);  // 选择较小的缩放比例

        // 使用比例缩放图片
        Image scaledImage = image.getScaledInstance((int) (width * ratio), (int) (height * ratio), Image.SCALE_SMOOTH);

        // 返回一个 JLabel 用于显示缩放后的图片
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);  // 居中显示图片
        return imageLabel;
    }
}
