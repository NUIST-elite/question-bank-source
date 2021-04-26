package edu.nuist.qdb.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Attribute")
public class Attribute {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    private long relationId;
    @Column(name = "attrkey")
    private String key;
    private String value;
}
