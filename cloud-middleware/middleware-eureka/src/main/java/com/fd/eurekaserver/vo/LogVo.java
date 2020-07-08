package com.fd.eurekaserver.vo;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Data
public class LogVo {
    @NotNull(message = "uuid不能为空")
    private String date;
    private String age;
    private String name;

    @Override
    public String toString() {
        return "LogVo{" +
                "date='" + date + '\'' +
                ", age='" + age + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        //fpp false positive
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 10000, 0.01);

        //置入元素，其实也有boolean类型返回，



        for(int i=0;i<10000;i++){
            bloomFilter.put(i);
        }

        //判断元素是否存在，true存在，false不存在。
        Map<Integer,Boolean> map = new HashMap<>();
        for(int i=1;i<20000;i++){
            boolean isContain=bloomFilter.mightContain(i);
            if (isContain){
                map.put(i,true);
            }
        }
        System.out.println(map.size());
    }

}
