package com.jw.shopping.dto;

import java.sql.Timestamp;

/*
CREATE TABLE `board` (
	`bId` INT NOT NULL AUTO_INCREMENT,
	`bName` VARCHAR(20) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`bTitle` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`bContent` VARCHAR(300) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`bDate` DATETIME NULL DEFAULT (now()),
	`bHit` INT NULL DEFAULT '0',
	`bGroup` INT NULL DEFAULT NULL,
	`bStep` INT NULL DEFAULT NULL,
	`bIndent` INT NULL DEFAULT NULL,
	`productId` INT NULL DEFAULT NULL,
	`boardType` ENUM('REVIEW','QNA') NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
	PRIMARY KEY (`bId`) USING BTREE,
	INDEX `FK_board_products` (`productId`) USING BTREE,
	CONSTRAINT `FK_board_products` FOREIGN KEY (`productId`) REFERENCES `products` (`product_id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=20
;
 */
public class Board {

	private int bId; // �ۼ��� ���̵�
	private String bName; // �ۼ��� �̸�
	private String bTitle; // �� ����
	private String bContent; // �� ����
	private Timestamp bDate; // �� �ۼ�����
	private int bHit; // ��ȸ��
	private int bGroup; // �Խ��� ���� �׷�
	private int bStep; // �Խ��� �� �亯 �ܰ�
	private int bIndent; // �Խ��� �亯�� �鿩����
	private int productId; // ��ǰ ID
	private BoardType boardType;
	
	public enum BoardType {
	    REVIEW, QNA;
	}
	
	public Board() {}

	public Board(int bId, String bName, String bTitle, String bContent, Timestamp bDate, int bHit, int bGroup,
			int bStep, int bIndent, int productId, BoardType boardType) {
		this.bId = bId;
		this.bName = bName;
		this.bTitle = bTitle;
		this.bContent = bContent;
		this.bDate = bDate;
		this.bHit = bHit;
		this.bGroup = bGroup;
		this.bStep = bStep;
		this.bIndent = bIndent;
		this.productId = productId;
		this.boardType = boardType;
	}

	public int getbId() {
		return bId;
	}

	public void setbId(int bId) {
		this.bId = bId;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getbTitle() {
		return bTitle;
	}

	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}

	public String getbContent() {
		return bContent;
	}

	public void setbContent(String bContent) {
		this.bContent = bContent;
	}

	public Timestamp getbDate() {
		return bDate;
	}

	public void setbDate(Timestamp bDate) {
		this.bDate = bDate;
	}

	public int getbHit() {
		return bHit;
	}

	public void setbHit(int bHit) {
		this.bHit = bHit;
	}

	public int getbGroup() {
		return bGroup;
	}

	public void setbGroup(int bGroup) {
		this.bGroup = bGroup;
	}

	public int getbStep() {
		return bStep;
	}

	public void setbStep(int bStep) {
		this.bStep = bStep;
	}

	public int getbIndent() {
		return bIndent;
	}

	public void setbIndent(int bIndent) {
		this.bIndent = bIndent;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }
}
