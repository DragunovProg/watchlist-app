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
    <div class="watchlist-controls-container">
      <div class="watchlist-filter-container">
        <div class="filter-naming-block">
          <svg width="25px" height="25px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M19 3H5C3.58579 3 2.87868 3 2.43934 3.4122C2 3.8244 2 4.48782 2 5.81466V6.50448C2 7.54232 2 8.06124 2.2596 8.49142C2.5192 8.9216 2.99347 9.18858 3.94202 9.72255L6.85504 11.3624C7.49146 11.7206 7.80967 11.8998 8.03751 12.0976C8.51199 12.5095 8.80408 12.9935 8.93644 13.5872C9 13.8722 9 14.2058 9 14.8729L9 17.5424C9 18.452 9 18.9067 9.25192 19.2613C9.50385 19.6158 9.95128 19.7907 10.8462 20.1406C12.7248 20.875 13.6641 21.2422 14.3321 20.8244C15 20.4066 15 19.4519 15 17.5424V14.8729C15 14.2058 15 13.8722 15.0636 13.5872C15.1959 12.9935 15.488 12.5095 15.9625 12.0976C16.1903 11.8998 16.5085 11.7206 17.145 11.3624L20.058 9.72255C21.0065 9.18858 21.4808 8.9216 21.7404 8.49142C22 8.06124 22 7.54232 22 6.50448V5.81466C22 4.48782 22 3.8244 21.5607 3.4122C21.1213 3 20.4142 3 19 3Z" stroke="#1C274C" stroke-width="1.5"/>
          </svg>
          <span>Filters</span>
        </div>
        <div class="search-filter-container">
          <span class="filter-title">Title:</span>
          <input class="search-filter__control title-search__item" type="search">
        </div>
        <div class="search-filter-container">
          <span class="filter-title">Type:</span>
          <input type="text" class="search-filter__control type-search__item">
        </div>
        <div class="search-filter-container">
          <span class="filter-title">Status:</span>
          <select class="search-filter__control status-select__item" name="status-select" id="">
            <option value="watched">Watched</option>
            <option value="watching">Watching</option>
            <option value="plan-to-watch">Plan to watch</option>
          </select>
        </div>
        <div class="search-filter-container">
          <span class="filter-title">Genres:</span>
          <input type="text" class="search-filter__control type-search__item">
        </div>
      </div>
    </div>
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
