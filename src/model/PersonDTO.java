package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;

import business.entitie.Person;

public class PersonDTO {
	
	private String id;
	private String num;
	private String name;
	private String firstName;
	private Integer age;
	

	
	public PersonDTO(String id, String num, String name, String firstName, Integer age) {
		super();
		this.id = id;
		this.num = num;
		this.name = name;
		this.firstName = firstName;
		this.age = age;
	}
	
	public PersonDTO(String num, String name, String firstName, Integer age) {
		super();
		this.num = num;
		this.name = name;
		this.firstName = firstName;
		this.age = age;
	}
	
	public PersonDTO(Person person){
		this(person.getId()+"",
				person.getNum(),
				person.getName(),
				person.getFirstName(),
				person.getAge());
	}
	
	public PersonDTO(Map<String, Object> personFromMongo){
		this( (String) personFromMongo.get("_id").toString(),
				(String) personFromMongo.get("num"),
				(String) personFromMongo.get("nom"),
				(String) personFromMongo.get("prenom"),
				Integer.valueOf((String) personFromMongo.get("age")));
	}
	
	//	((Double) personFromMongo.get("age")).intValue());
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
/*
	public static PersonDTO getPersonDTOFromPerson(PersonEntity personEntity) {
		return new PersonDTO(personEntity.getId(), personEntity.getNum(), personEntity.getName(), personEntity.getFirstName(), personEntity.getAge());
	}*/

	public Person getPerson() {
		return new Person(Long.valueOf(this.id), this.num, this.name, this.firstName, this.age , new ArrayList<>());
	}

	public Map<String, Object> getMapPerson() {
		Map<String, Object> mapPerson = new HashMap<>();
		//mapPerson.put("_id", getId());
		if(getId() != null) {
			mapPerson.put("_id", new ObjectId(getId()));
		}
		mapPerson.put("num", getNum());
		mapPerson.put("nom", getName());
		mapPerson.put("prenom", getFirstName());
		mapPerson.put("age", getAge().toString());
		
		return mapPerson;
	}

	@Override
	public String toString() {
		return firstName + " " + name + " (" + age+ ")("+num+")";
	}
	
	
	
}
