package org.web.cape.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the mgr_role_user database table.
 * 
 */
@Entity
@Table(name="mgr_role_user")
@NamedQuery(name="MgrRoleUser.findAll", query="SELECT m FROM MgrRoleUser m")
public class MgrRoleUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="role_id")
	private BigInteger roleId;

	@Column(name="user_id")
	private BigInteger userId;

	public MgrRoleUser() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigInteger getRoleId() {
		return this.roleId;
	}

	public void setRoleId(BigInteger roleId) {
		this.roleId = roleId;
	}

	public BigInteger getUserId() {
		return this.userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

}