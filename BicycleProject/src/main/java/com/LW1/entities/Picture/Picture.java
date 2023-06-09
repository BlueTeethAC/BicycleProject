package com.LW1.entities.Picture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//图片实体类（接收用的）
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture {

    String pictureName;//图片名
    String fileBase64;//base64编码

}
