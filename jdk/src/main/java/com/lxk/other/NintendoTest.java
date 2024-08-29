package com.lxk.other;

import com.google.common.collect.Lists;
import com.lxk.bean.model.SwitchParts;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * @author LiXuekai on 2024/8/7
 */
public class NintendoTest {

    @Test
    public void show() {
        List<SwitchParts> parts = Lists.newArrayList();
        parts.addAll(game());
        parts.addAll(hardware());
        int sum = parts.stream().mapToInt(SwitchParts::getValue).sum();
        System.out.println("消费了：" + sum + " ¥ ");
    }

    private List<SwitchParts> game() {
        List<SwitchParts> list = Lists.newArrayList();
        list.add(new SwitchParts("塞尔达传说-旷野之息", LocalDate.of(2024, 8, 5), 234));
        list.add(new SwitchParts("健身环大冒险", LocalDate.of(2024, 7, 20), 334));
        list.add(new SwitchParts("哈迪斯", LocalDate.of(2024, 7, 18), 149));
        list.add(new SwitchParts("星之卡比-探索发现", LocalDate.of(2024, 7, 16), 232));
        list.add(new SwitchParts("塞尔达传说-王国之泪", LocalDate.of(2024, 7, 6), 258));
        list.add(new SwitchParts("星之卡比-wii豪华版", LocalDate.of(2024, 2, 24), 236));
        list.add(new SwitchParts("塞尔达传说-王国之泪", LocalDate.of(2024, 2, 19), 239));
        list.add(new SwitchParts("狂野飙车-DLC", LocalDate.of(2024, 2, 11), 298));
        list.add(new SwitchParts("超级马里奥-派对", LocalDate.of(2024, 1, 11), 238));
        list.add(new SwitchParts("超级马里奥-惊奇", LocalDate.of(2024, 1, 10), 241));
        return list;
    }

    private List<SwitchParts> hardware() {
        List<SwitchParts> list = Lists.newArrayList();
        list.add(new SwitchParts("Switch NS PRO 手柄 塞尔达传说-王国之泪 全新", LocalDate.of(2024, 8, 29), 316));
        list.add(new SwitchParts("内存卡-64G", LocalDate.of(2024, 5, 18), 38));
        list.add(new SwitchParts("保护壳", LocalDate.of(2023, 12, 1), 25));
        list.add(new SwitchParts("贴膜", LocalDate.of(2023, 12, 1), 19));
        list.add(new SwitchParts("手柄", LocalDate.of(2023, 12, 1), 123));
        list.add(new SwitchParts("VIP", LocalDate.of(2023, 12, 1), 39));
        list.add(new SwitchParts("package", LocalDate.of(2023, 11, 30), 26));
        list.add(new SwitchParts("SWITCH-O-LED", LocalDate.of(2023, 11, 29), 1746));
        return list;
    }
}
