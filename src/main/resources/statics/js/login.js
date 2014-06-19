var userIdCookie = "wcnUserId",
	userNameCookie = "wcnUserName",
	firstNameCookie = "wcnFirstName",
	lastNameCookie = "wcnLastName";

function setCookie(name, value) {
	return $.cookie(name, value);
}

function getCookie(name) {
	return $.cookie(name);
}

function register(userName, firstName, lastName) {
	var registerLocally = function (data) {
		var userId = data.userId,
			userName = data.userName,
			firstName = data.firstName,
			lastName = data.lastName;
		
		setCookie(userIdCookie, userId);
		setCookie(userNameCookie, userName);
		setCookie(firstNameCookie, firstName);
		setCookie(lastNameCookie, lastName);
		
		hideLoginForm();
		focusPostField();
	};
	$.ajax({
		url: "user",
		method: "POST",
		data: JSON.stringify({
			userName: userName,
			firstName: firstName,
			lastName: lastName
		}),
		dataType: 'json',
		contentType: 'application/json',
		processData: false
	}).done(registerLocally).fail(registerFailed);
}

function getCurrentUserInfo() {
	var userId = getCookie(userIdCookie),
		userName = getCookie(userNameCookie),
		firstName = getCookie(firstNameCookie),
		lastName = getCookie(lastNameCookie);
	
	if (userId) {
		return {
			userId: userId,
			userName: userName,
			firstName: firstName,
			lastName: lastName
		};
	} else {
		return null;
	}
}

function registerFailed(response) {
	if (response.status === 400) {
		alert(response.responseText);
	} else {
		alert("Received " + response.status + ": " + response.responseText);
	}
}
