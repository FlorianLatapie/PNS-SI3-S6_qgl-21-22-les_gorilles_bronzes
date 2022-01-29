package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "mode", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegattaGoal.class, name = "REGATTA"),
        @JsonSubTypes.Type(value = RegattaGoal.class, name = "BATTLE")
})
public interface Goal{}
