package me.yec.service.impl;

import lombok.RequiredArgsConstructor;
import me.yec.model.entity.item.GenshinCharacter;
import me.yec.model.entity.item.GenshinItem;
import me.yec.model.entity.item.GenshinItemType;
import me.yec.model.entity.item.GenshinWeapon;
import me.yec.repository.GenshinCharacterRepository;
import me.yec.repository.GenshinWeaponRepository;
import me.yec.service.GenshinItemService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yec
 * @date 12/4/20 9:39 PM
 */
@Service
@RequiredArgsConstructor
public class GenshinItemServiceImpl implements GenshinItemService {

    private final GenshinCharacterRepository genshinCharacterRepository;
    private final GenshinWeaponRepository genshinWeaponRepository;

    @Override
    public List<? extends GenshinItem> findAllGenshinItem(String type, String sort, String order) {
        // 如果未指定类型将返回所有的武器和角色信息（...）
        if (!GenshinItemType.CHARACTER.name().equals(type.toUpperCase())
                && !GenshinItemType.WEAPON.name().equals(type.toUpperCase())) {
            List<GenshinCharacter> characters = genshinCharacterRepository.findAll();
            List<GenshinWeapon> weapons = genshinWeaponRepository.findAll();
            List<GenshinItem> genshinItems = new ArrayList<>();
            genshinItems.addAll(characters);
            genshinItems.addAll(weapons);
            return genshinItems;
        } else {
            // 判断排序方向
            Sort.Direction direction = checkOrder(order);

            // sort 默认值为 id, direction 默认为 asc （也就是安装 id 升序）
            if (GenshinItemType.CHARACTER.name().equals(type.toUpperCase())) {
                return genshinCharacterRepository.findAll(Sort.by(direction, sort));
            } else {
                return genshinWeaponRepository.findAll(Sort.by(direction, sort));
            }
        }
    }

    public List<GenshinItem> findGiftById(List<Long> itemIds) {
        List<GenshinCharacter> characters = genshinCharacterRepository.findAllById(itemIds);
        List<GenshinWeapon> weapons = genshinWeaponRepository.findAllById(itemIds);
        List<GenshinItem> genshinItems = new ArrayList<>();
        for (Long itemId : itemIds) {
            for (GenshinCharacter character : characters) {
                if (character.getId().equals(itemId)) {
                    genshinItems.add(character);
                    break;
                }
            }

            for (GenshinWeapon weapon : weapons) {
                if (weapon.getId().equals(itemId)) {
                    genshinItems.add(weapon);
                    break;
                }
            }
        }
        return genshinItems;
    }

    private Sort.Direction checkOrder(String order) {
        if ("asc".equals(order)) {
            return Sort.Direction.ASC;
        } else if ("desc".equals(order)) {
            return Sort.Direction.DESC;
        } else {
            return Sort.Direction.ASC;
        }
    }


    @Override
    public List<GenshinCharacter> findAllGenshinCharacter(String sort, String order) {
        Sort.Direction direction = checkOrder(order);
        return genshinCharacterRepository.findAll(Sort.by(direction, sort));
    }

    @Override
    public List<GenshinWeapon> findAllGenshinWeapon(String sort, String order) {
        Sort.Direction direction = checkOrder(order);
        return genshinWeaponRepository.findAll(Sort.by(direction, sort));
    }
}