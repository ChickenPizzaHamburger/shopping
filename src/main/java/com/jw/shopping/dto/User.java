package com.jw.shopping.dto;

import java.time.LocalDate;
import org.springframework.stereotype.Repository;

/*
CREATE TABLE `users` (
	`num` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`id` VARCHAR(12) NOT NULL COLLATE 'utf8mb3_general_ci',
	`password` VARCHAR(60) NULL DEFAULT NULL COMMENT 'BCrypt = 60' COLLATE 'utf8mb3_general_ci',
	`email` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',
	`name` VARCHAR(6) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',
	`sex` TINYINT UNSIGNED NULL DEFAULT NULL COMMENT '0: Male, 2: Female',
	`birthday` DATE NULL DEFAULT NULL,
	`role` TINYINT UNSIGNED NULL DEFAULT NULL COMMENT '0: Root, 1: Admin... 255: User',
	`createdAt` DATETIME NOT NULL,
	PRIMARY KEY (`num`) USING BTREE,
	UNIQUE INDEX `id` (`id`) USING BTREE,
	UNIQUE INDEX `email` (`email`) USING BTREE
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=31
;
 */
@Repository
public class User {
	private String id;
	private String password;
	private String email;
	private String name;
	private Sex sex;
	private LocalDate birthday;
	private Role role;

	public enum Sex {
	    MALE(0), FEMALE(1);

	    private final int value;

	    Sex(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }

	    public static Sex fromString(String sex) {
	        if ("MALE".equalsIgnoreCase(sex)) {
	            return MALE;
	        } else if ("FEMALE".equalsIgnoreCase(sex)) {
	            return FEMALE;
	        } else {
	            throw new IllegalArgumentException("Invalid sex value: " + sex);
	        }
	    }

	    public static Sex fromInt(int value) {
	        for (Sex sex : values()) {
	            if (sex.getValue() == value) {
	                return sex;
	            }
	        }
	        return null;
	    }
	}

    public enum Role {
        ROOT(1), ADMIN(2), USER(255);

        private final int value;

        Role(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Role valueOf(int value) {
            for (Role role : values()) {
                if (role.getValue() == value) {
                    return role;
                }
            }
            return null;
        }
    }
	
	public User() {}

	public User(String id, String password, String email, String name, Sex sex, LocalDate birthday, Role role) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
