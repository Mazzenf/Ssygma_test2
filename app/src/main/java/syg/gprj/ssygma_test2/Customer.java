package syg.gprj.ssygma_test2;

public class Customer
{
    public String fullName, birth_date, email, city, password, phoneNumber, license_image;

    public Customer() {}

    public Customer (String fullName, String birth_date, String email, String city, String password, String phoneNumber)
    {
        this.birth_date = birth_date;
        this.fullName=fullName;
        this.email=email;
        this.city=city;
        this.password=password;
        this.phoneNumber=phoneNumber;
    }

    public Customer (String license_image)
    {
        this.license_image = license_image;
    }

}
