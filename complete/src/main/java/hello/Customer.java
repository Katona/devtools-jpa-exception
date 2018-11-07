// tag::sample[]
package hello;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    @Type(type = "hello.JsonType")
    private PhoneNumber phoneNumber;

    protected Customer() {}

    public Customer(String firstName, String lastName, PhoneNumber phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s', phoneNumber=%s]",
                id, firstName, lastName, phoneNumber);
    }

// end::sample[]

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
}

