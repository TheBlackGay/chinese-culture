package com.chinese.culture.admin.convert;

import com.chinese.culture.admin.api.dto.request.AstrolabeRequestDTO;
import com.chinese.culture.admin.api.dto.response.AstrolabeResponseDTO;
import com.chinese.culture.admin.api.dto.response.PalaceResponseDTO;
import com.chinese.culture.admin.api.dto.response.StarDTO;
import com.chinese.culture.admin.common.core.iztro.data.AstrolabeBO;
import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.persist.bo.AstrolabeQueryBO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>文件名称：chinese-culture  </p>
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2017-2099</p>
 * <p>公   司： 八维通 </p>
 * <p>内容摘要： </p>
 * <p>其他说明： </p>
 * <p>完成日期：2025/1/22</p>
 *
 * @author zhongjiafeng@bwton.com
 * @version 1.0
 * @Date :Created by 2025/1/22.
 */
public class AstrolabeConvert {

    public static AstrolabeQueryBO toAstrolabeQueryBO(AstrolabeRequestDTO requestDTO) {

        if (Objects.isNull(requestDTO)) {
            return null;
        }

        AstrolabeQueryBO astrolabeQueryBO = new AstrolabeQueryBO();
        astrolabeQueryBO.setBirthYear(requestDTO.getBirthYear());
        astrolabeQueryBO.setBirthMonth(requestDTO.getBirthMonth());
        astrolabeQueryBO.setBirthDay(requestDTO.getBirthDay());
        astrolabeQueryBO.setBirthHour(requestDTO.getBirthHour());
        astrolabeQueryBO.setGender(requestDTO.getGender());
        astrolabeQueryBO.setIsLunar(requestDTO.getIsLunar());
        return astrolabeQueryBO;

    }

    public static AstrolabeResponseDTO convertToResponseDTO(AstrolabeBO responseBO) {

        if (Objects.isNull(responseBO)) {
            return null;
        }

        AstrolabeResponseDTO responseDTO = new AstrolabeResponseDTO();
        responseDTO.setPalaces(convertPalaceList(responseBO.getPalaces()));
        return responseDTO;
    }

    private static List<PalaceResponseDTO> convertPalaceList(List<PalaceBO> palaces) {
        if (Objects.isNull(palaces)) {
            return null;
        }
        return palaces.stream()
                .map(AstrolabeConvert::convertPalace)
                .collect(Collectors.toList());
    }

    private static PalaceResponseDTO convertPalace(PalaceBO palace) {
        if (Objects.isNull(palace)) {
            return null;
        }
        PalaceResponseDTO responseDTO = new PalaceResponseDTO();
        responseDTO.setIndex(palace.getIndex());
        responseDTO.setName(palace.getName());
        responseDTO.setBranch(palace.getBranch());
        responseDTO.setHeavenlyStem(palace.getHeavenlyStem());
        responseDTO.setMajorStars(convertStarList(palace.getMajorStars()));
        responseDTO.setMinorStars(convertStarList(palace.getMinorStars()));
        responseDTO.setAdjectiveStars(convertStarList(palace.getAdjectiveStars()));
        responseDTO.setMutagens(palace.getMutagens());
        responseDTO.setMing(palace.isMingGong());
        responseDTO.setBody(palace.isShenGong());
        responseDTO.setAttribute(palace.getAttribute().ordinal());
        responseDTO.setFiveElements(palace.getFiveElements().ordinal());
        return responseDTO;
    }

    private static List<StarDTO> convertStarList(List<StarBO> stars) {
        if (Objects.isNull(stars)) {
            return null;
        }
        return stars.stream()
                .map(AstrolabeConvert::convertStar)
                .collect(Collectors.toList());
    }

    private static StarDTO convertStar(StarBO star) {
        if (Objects.isNull(star)) {
            return null;
        }
        StarDTO starDTO = new StarDTO();
        starDTO.setName(star.getName().toString());
        starDTO.setBrightness(star.getBrightnessInfo().getBrightness().toString());
        List<String> mutagens = new ArrayList<>();
        if (star.getMutagenInfo() != null && star.getMutagenInfo().getMutagens() != null) {
            mutagens = star.getMutagenInfo().getMutagens().stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        starDTO.setMutagens(mutagens);
        return starDTO;
    }

}
