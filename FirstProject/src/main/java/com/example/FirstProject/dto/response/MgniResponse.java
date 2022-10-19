package com.example.FirstProject.dto.response;

import com.example.FirstProject.model.mgniEntity.Mgni;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name="MgniResponse")
public class MgniResponse {
    private Mgni mgni;
}
