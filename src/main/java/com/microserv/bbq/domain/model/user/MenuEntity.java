package com.microserv.bbq.domain.model.user;

import com.microserv.bbq.domain.common.ICrud;
import com.microserv.bbq.domain.factory.RepoFactory;
import com.microserv.bbq.domain.repository.UserRoleMenuRepo;
import com.microserv.bbq.infrastructure.general.toolkit.SequenceUtils;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jockeys
 * @date 2020/9/13
 */
@Data
@ApiModel
@Accessors(chain = true)
public class MenuEntity implements ICrud<MenuEntity> {

	private static UserRoleMenuRepo userRoleMenuRepo = RepoFactory.get(UserRoleMenuRepo.class);
	//field
	private String menuId;
	private String parentId;
	private String name;
	private String url;
	private Integer type;
	private String icon;
	private Integer orderNum;

	public boolean isRootMenu() {
		return this.type == 0;
	}

	public boolean isSubMenuOf(String parentId) {
		return (this.parentId.equals(parentId));
	}

	@Override
	public boolean delete() {
		return userRoleMenuRepo.delete(this);
	}

	@Override
	public MenuEntity get() {
		return userRoleMenuRepo.selectMenuById(this.menuId);
	}

	@Override
	public MenuEntity saveOrUpdate() {
		if (this.menuId == null) {
			userRoleMenuRepo.insert(this.setMenuId(SequenceUtils.uuid32()));
		} else {
			userRoleMenuRepo.update(this);
		}
		return this;
	}
}
