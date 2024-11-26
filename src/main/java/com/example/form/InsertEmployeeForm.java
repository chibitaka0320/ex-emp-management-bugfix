package com.example.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 従業員情報登録時に使用するフォーム.
 *
 * @author T.Araki
 *
 */
public class InsertEmployeeForm {
    /** 従業員名 */
    @NotBlank(message = "従業員名を入力してください")
    private String name;
    /** 画像 */
    private MultipartFile image;
    /** 性別 */
    @NotBlank(message = "性別を入力してください")
    private String gender;
    /** 入社日 */
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "入社日を選択してください")
    private String hireDate;
    /** メールアドレス */
    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレス形式で入力してください")
    private String mailAddress;
    /** 郵便番号 */
    @Pattern(regexp = "^\\d{3}-\\d{4}$", message = "郵便番号形式で入力してください(ハイフンあり)")
    private String zipCode;
    /** 住所 */
    @NotBlank(message = "住所を入力してください")
    private String address;
    /** 電話番号 */
    @Pattern(regexp = "^\\d{2,4}-\\d{2,4}-\\d{4}$", message = "電話番号形式で入力してください(ハイフンあり)")
    private String telephone;
    /** 給料 */
    @Pattern(regexp = "^[0-9]+$", message = "給料は数値で入力してください")
    private String salary;
    /** 特性 */
    @NotBlank(message = "特性を入力してください")
    @Size(max = 500, message = "特性は500字以内で入力してください")
    private String characteristics;
    /** 扶養人数 */
    @Pattern(regexp = "^[0-9]+$", message = "給料は数値で入力してください")
    private String dependentsCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getDependentsCount() {
        return dependentsCount;
    }

    public void setDependentsCount(String dependentsCount) {
        this.dependentsCount = dependentsCount;
    }

    @Override
    public String toString() {
        return "InsertEmployeeForm [name=" + name + ", image=" + image + ", gender=" + gender
                + ", hireDate=" + hireDate + ", mailAddress=" + mailAddress + ", zipCode=" + zipCode + ", address="
                + address + ", telephone=" + telephone + ", salary=" + salary + ", characteristics=" + characteristics
                + ", dependentsCount=" + dependentsCount + "]";
    }

}
