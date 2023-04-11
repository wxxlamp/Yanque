package cn.wxxlamp.yanque.repo.model;

import lombok.Data;

/**
 * @author chenkai
 * @version 2023/4/9 21:48
 */
@Data
public class YuqueApiResponse <ABILITY, DATA> {

    private ABILITY abilities;

    private DATA data;
}
