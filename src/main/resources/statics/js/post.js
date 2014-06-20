function post(content, extraCallback) {
	var user = getCurrentUserInfo();
	if (user) {
		var userName = user.userName;
		$.ajax({
			url: "post/" + userName,
			method: "POST",
			data: content,
			dataType: 'json',
			contentType: 'application/json',
			processData: false
		}).done(function (post) {
			addPostToBoard(post);
			extraCallback();
		}).fail(function (response) {
			if (response.status === 401) {
				showLoginForm();
			}
		});
	} else {
		alert("Login first");
	}
}

var PostLoader = new function () {
	this.lastSince = 0;
	
	this.loadPosts = function () {
		var me = this,
			user = getCurrentUserInfo(),
			userId = (user && this.lastSince !== 0)? user.userId: -1;
		$.ajax({
			url: "post?since=" + this.lastSince,
			method: "GET",
			dataType: 'json'
		}).done(function (posts) {
			var maxTime = -1;
			for (var i = 0; i < posts.length; i++) {
				var post = posts[i];
				maxTime = Math.max(maxTime, post.timeMs);
				if (post.author.userId !== userId) {
					addPostToBoard(posts[i]);
				}
			}
			if (maxTime !== -1) {
				me.lastSince = maxTime + 1;
			}
		});
		this.lastSince = new Date().getTime();
	}
	
	this.repeat = function () {
		PostLoader.loadPosts();
		setTimeout(PostLoader.repeat, 2000);
	}
}

PostLoader.repeat();