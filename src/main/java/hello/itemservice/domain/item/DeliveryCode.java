package hello.itemservice.domain.item;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * FAST : ���� ���
 * NORMAL : �Ϲ� ���
 * SLOW : ���� ���
 *
 * @author kodin
 *
 */


@Data
@RequiredArgsConstructor
public class DeliveryCode {
	private String code;
	private String displayName;
	
	
	public DeliveryCode(String code, String displayName) {
		this.code = code;
		this.displayName = displayName;
	}
	
}
