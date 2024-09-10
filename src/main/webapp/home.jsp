<%--
Created by IntelliJ IDEA.
User: dragu
Date: 9/4/2024
Time: 11:50 PM
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
  <title>Watchlist</title>
  <link rel="stylesheet" href="static/styles/styles.css">
  <link rel="stylesheet" href="static/styles/home.css">
  <script src="static/scripts/account-submenu-widget.js" defer></script>
</head>
<body>
<div class="container">
  <div class="header-container">
    <div class="logo-container">
      <span id="logo__item"><a href="/">TrackMate</a></span>
    </div>
    <div class="account-preview-container">
      <span class="account-name__item">${sessionScope.user.firstName} ${sessionScope.user.lastName}</span>
      <div class="account-menu-container account-menu-hidden">
        <span class="account-logout__item" style="display: block;">
          <a href="/logout">Logout</a>
        </span>
      </div>
    </div>

  </div>
  <div class="main-container">
    <div class="watchlist-container">
      <c:forEach var="item" items="${watchlistItems}">
        <div class="watchlist__item">
          <div class="watchlist-image__item">
            <img id="image" src="${item.picture}" alt="${item.title}" width="100">
          </div>
          <div class="watchlist-preview-content__item">
            <div class="watchlist__item-header__item">
              <span class="watchlist-container-title">${item.title}</span>
              <div class="watchlist-container-status">
                <span class="status__item" style="background: ${item.status.backgroundColor}">${item.status.statusName}</span>
              </div>
            </div>
            <div class="watchlist-preview-content__item">
              <span class="watchlist-container-type">type: ${item.type}</span>
              <span class="watchlist-container-genre">genres: ${item.genre}</span>
              <span class="watchlist-container-released-year">Released year: ${item.releaseYear}</span>
            </div>
          </div>
        </div>
      </c:forEach>
    </div>
  </div>
  <div class="footer-container"></div>
</div>
</body>
</html>
