function loadMatches() {
	resetMatchBoard();
	$.ajax({
		url: "match",
		method: "GET",
		dataType: 'json'
	}).done(function (matches) {
		for (var i = 0; i < matches.length; i++) {
			var match = matches[i];
			addMatch(match);
		}
	});
}