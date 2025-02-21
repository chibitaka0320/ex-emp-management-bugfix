package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Employee;
import com.example.form.InsertEmployeeForm;
import com.example.form.UpdateEmployeeForm;
import com.example.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 *
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 使用するUpdateEmployeeフォームオブジェクトをリクエストスコープに格納する.
	 *
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	/**
	 * 使用するInsertEmployeeフォームオブジェクトをリクエストスコープに格納する.
	 *
	 * @return フォーム
	 */
	@ModelAttribute
	public InsertEmployeeForm setUInsertEmployeeForm() {
		return new InsertEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 *
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@GetMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 *
	 * @param id    リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@GetMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 *
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@PostMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員情報を名前で検索する(曖昧検索)
	/////////////////////////////////////////////////////
	/**
	 * 従業員情報を検索します.
	 *
	 * @param name  従業員名
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@GetMapping("/search")
	public String search(String name, Model model) {
		List<Employee> employeeList = employeeService.searchByName(name);
		if (employeeList.size() == 0) {
			model.addAttribute("message", "1件もありませんでした");
			employeeList = employeeService.showList();
		}

		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員情報を名前で検索する(オートコンプリート)
	/////////////////////////////////////////////////////
	/**
	 * 従業員情報の名前一覧を取得します.
	 *
	 * @return 従業員情報の名前一覧
	 */
	@ResponseBody
	@GetMapping("/autocomplete")
	public List<String> autocomplete() {
		return employeeService.getNameList();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員追加を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員追加画面を出力します.
	 *
	 * @return 従業員一覧画面
	 */
	@GetMapping("/toInsert")
	public String toInsert() {
		return "employee/insert";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員情報を追加する
	/////////////////////////////////////////////////////
	/**
	 * 従業員情報を追加します.
	 *
	 * @param form 従業員追加用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@PostMapping("/insert")
	public String insert(@Validated InsertEmployeeForm form, BindingResult result) {
		if (employeeService.searchByEmail(form.getMailAddress()) != null) {
			result.rejectValue("mailAddress", "email.alreadyExists", "既に登録されているメールアドレスです");
		}
		if (form.getImage().getSize() <= 0) {
			result.rejectValue("image", "image.notfound", "画像を選択してください");
		}
		if (result.hasErrors()) {
			return toInsert();
		}
		employeeService.insert(form);
		return "redirect:/employee/showList";
	}
}
