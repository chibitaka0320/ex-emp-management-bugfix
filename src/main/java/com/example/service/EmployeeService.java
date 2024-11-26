package com.example.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.Employee;
import com.example.form.InsertEmployeeForm;
import com.example.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 *
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員情報を全件取得します.
	 *
	 * @return 従業員情報一覧
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}

	/**
	 * 従業員情報を取得します.
	 *
	 * @param id ID
	 * @return 従業員情報
	 * @throws org.springframework.dao.DataAccessException 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}

	/**
	 * 従業員情報を更新します.
	 *
	 * @param employee 更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}

	/**
	 * 名前が一致(曖昧)した従業員情報を取得します.
	 *
	 * @param name 従業員名
	 * @return 従業員情報一覧
	 */
	public List<Employee> searchByName(String name) {
		List<Employee> employeeList = null;
		if (name.isBlank()) {
			employeeList = showList();
		} else {
			employeeList = employeeRepository.findByName(name);
		}
		return employeeList;
	}

	/**
	 * 従業員情報を追加します.
	 *
	 * @param form 従業員フォーム
	 */
	public void insert(InsertEmployeeForm form) {
		try {
			// 画像アップロードの準備
			MultipartFile file = form.getImage();
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String fileName = UUID.randomUUID().toString() + extension;

			// 従業員情報準備
			Employee employee = new Employee();
			BeanUtils.copyProperties(form, employee);

			// ファイル名セット
			employee.setImage(fileName);

			// 入社日セット
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			employee.setHireDate(sdf.parse(form.getHireDate()));

			// 給料セット
			employee.setSalary(Integer.parseInt(form.getSalary()));

			// 扶養家族セット
			employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));

			employeeRepository.insert(employee);

			// 画像アップロード
			String filePath = "src/main/resources/static/employee/" + fileName;
			byte[] content = file.getBytes();
			Files.write(Paths.get(filePath), content);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * メールアドレスから従業員情報を取得します.
	 *
	 * @param mailAddress メールアドレス
	 * @return 従業員情報 存在しない場合はnullが返る
	 */
	public Employee searchByEmail(String mailAddress) {
		return employeeRepository.findByMailAddress(mailAddress);
	}
}
