package toyproject.fifa.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class RandomItem extends Item {
    // TODO 선수ID의 범위 내에서 랜덤한 선수ID값을 뽑을 수 있는 메소드 구현
    public static Long getPlayerId(){


        return 0L;
    }
}


