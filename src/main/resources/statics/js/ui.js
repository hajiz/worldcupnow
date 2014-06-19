var boardSelector = "#board",
	firstNameFieldSelector = "#firstNameField",
	lastNameFieldSelector = "#lastNameField",
	userNameFieldSelector = "#userNameField",
	loginButtonSelector = "#loginButton",
	messageFieldSelector = "#messageField",
	sendButtonSelector = "#sendButton",
	loginFormSelector = "#loginForm",
	boardContainerSelector = "#boardContainer",
	loginDialogSelector = "#loginDialog",
	loginDialogButtonSelector = "#loginDialogButton",
	matchBoardSelector = "#matchList";


var postTemplate = "<tr><td><b>{firstName} {lastName}:</b> {content}</td></tr>",
	matchTemplace = 
		"<tr><td>" +
			"<div class='match-parts flag {flaghome}'>{home} <b>{homeScore}</b></div>" +
			"<div class='match-parts flag {flagaway}'>{away} <b>{awayScore}</b></div>" +
			"<div>{date}</div>" +
		"</td></tr>";

function addPostToBoard (post) {
	var author = post.author,
		content = post.content;
	
	$(boardSelector).append(
			postTemplate
				.replace(/{firstName}/, author.firstName)
				.replace(/{lastName}/, author.lastName)
				.replace(/{content}/, content)
			);
}

function addMatch(match) {
	var homeScore = (match.homeScore === -1)? '': match.homeScore,
			awayScore = (match.awayScore === -1)? '': match.awayScore;
	$(matchBoardSelector).append(
			matchTemplace
				.replace(/{home}/, match.home)
				.replace(/{away}/, match.away)
				.replace(/{date}/, formatDate(match.timeStamp))
				.replace(/{flaghome}/, match.homeFlag)
				.replace(/{flagaway}/, match.awayFlag)
				.replace(/{homeScore}/, homeScore)
				.replace(/{awayScore}/, awayScore)
			);
}

function resetMatchBoard() {
	$(matchBoardSelector).html("");
}

function formatDate(date) {
	date = new Date(date);
	return date.toDateString() +
				' ' +
				date.getHours() +
				':' +
				(date.getMinutes() < 10? '0': '') +
				date.getMinutes();
}

var onLoginEnterPressed = function (event) {
	if (event.keyCode === 13) {
		login();
	}
};

var onSendEnterPressed = function (event) {
	if (event.keyCode === 13) {
		sendMessage();
	}
}

$(document).ready(attachUiListeners);

function attachUiListeners () {
	$(firstNameFieldSelector).keydown(onLoginEnterPressed);
	$(lastNameFieldSelector).keydown(onLoginEnterPressed);
	$(userNameFieldSelector).keydown(onLoginEnterPressed);
	$(loginButtonSelector).click(login);
	
	$(messageFieldSelector).keydown(onSendEnterPressed);
	$(sendButtonSelector).click(sendMessage);
	
	if (getCurrentUserInfo() !== null) {
		hideLoginForm();
	} else {
		showLoginForm();
	}
	
	scrollBoardToBottom();
	
	$(loginDialogButtonSelector).click(showLoginForm);
	
	loadMatches();
}

function getFirstName() {
	return $(firstNameFieldSelector).val();
}

function getLastName() {
	return $(lastNameFieldSelector).val();
}

function getUserName() {
	return $(userNameFieldSelector).val();
}

function login() {
	register(getUserName(), getFirstName(), getLastName());
	return false;
}

function getMessage() {
	return $(messageFieldSelector).val();
}

function clearMessage() {
	$(messageFieldSelector).val("");
}

function sendMessage() {
	post(getMessage(), scrollBoardToBottom);
	clearMessage();
	return false;
}

function scrollBoardToBottom() {
	$(boardContainerSelector).animate({ "scrollTop": $(boardContainerSelector).height() + 300 + "px" });
}

function hideLoginForm() {
	$(loginDialogSelector).modal('hide');
}

function showLoginForm() {
	$(loginDialogSelector).modal('show');
}

function focusPostField() {
	$(messageFieldSelector).focus();
}