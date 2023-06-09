package com.LW1.entities.Picture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//图片包装类 用于存在 mongodb 中用的
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureInfo {

    String id;//存在mongodb中的id
    String base64Picture;//base64编码

}
