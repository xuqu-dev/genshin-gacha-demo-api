package me.yec.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 原神角色实体类
 *
 * @author yec
 * @date 12/4/20 8:12 PM
 */
@Data
@Entity
@Table(name = "genshin_character")
@EqualsAndHashCode(callSuper = true)
public class GenshinCharacter extends GenshinItem {

    @Id
    private long id;

    // 角色名称
    private String name;

    // 角色头像
    private String avatar;

    // 角色属性
    @Column(name = "character_attribute_id")
    private Integer CharacterAttrId;

    // 角色星级（5星，4星...）
    @Column(name = "rank_v")
    private Integer rankV;

    public GenshinCharacter() {
        this.setType(ItemType.CHARACTER);
    }
}
