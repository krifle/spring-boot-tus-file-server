package io.tus.wndflwr.controller.response.model;

public class CommonResult {

	private boolean success;
	private String message;
	private Object result;

	private CommonResult(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public static CommonResult success(String message) {
		CommonResult commonResult = new CommonResult(true);
		commonResult.setMessage(message);
		return commonResult;
	}

	public static CommonResult fail(String message) {
		CommonResult commonResult = new CommonResult(false);
		commonResult.setMessage(message);
		return commonResult;
	}

	public static CommonResult success(String message, Object result) {
		CommonResult commonResult = new CommonResult(true);
		commonResult.setMessage(message);
		commonResult.setResult(result);
		return commonResult;
	}

	public static CommonResult fail(String message, Object result) {
		CommonResult commonResult = new CommonResult(false);
		commonResult.setMessage(message);
		commonResult.setResult(result);
		return commonResult;
	}
}
