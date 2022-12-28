package com.vti.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.vti.converter.DepartmentTypeConverter;

import lombok.Data;
import lombok.NonNull;

@Entity
@Table(name = "Department")
@Data
@DynamicInsert
public class Department {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", length = 50, nullable = false, unique = true)
	private String name;
	
	@Column(name = "total_member")
	private int totalMember;
	
//	@Column(name = "type", columnDefinition = "ENUM('DEV', 'TEST', 'SCRUM_MASTER', 'PM')")
//	@Enumerated(EnumType.STRING)
//	private DepartmentType type;
	
	@Column(name = "type")
	@Convert(converter = DepartmentTypeConverter.class)
	private DepartmentType type;
	
	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;
	
	//@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany(mappedBy = "department")
	private List<Account> accounts;
	
	//Set department null for all accounts before delete department
	@PreRemove
	public void nullifyAccounts() {
		accounts.forEach(ac -> ac.setDepartment(null));
	}
	
	public enum DepartmentType {
		DEV("D"), TEST("T"), SCRUM_MASTER("S"), PM("P");
		
		private String type;
		
		DepartmentType(String type) {
			this.type = type;		
		}
		
		public String getType() {
			return type;
		}
		
		public static DepartmentType toEnum(String type) {
			for (DepartmentType item : DepartmentType.values()) {
				if (item.getType().equals(type)) return item;
			}
			return null;
		}
		
		public static DepartmentType toEnum2(String type) {
			for (DepartmentType item : DepartmentType.values()) {
				if (item.toString().equals(type)) return item;
			}
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "[id = " + id + "; name = " + name + 
				"; totalMember = " + totalMember + "; type = " + type + 
				"; createdAt = " + createdAt + "; account = " + accounts + "]";
	}

}


























