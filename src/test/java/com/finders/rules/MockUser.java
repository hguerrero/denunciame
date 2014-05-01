package com.finders.rules;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;

public class MockUser implements User {

    private long id;
    private String name;
    private String screenName;
    private String location;
    private String description;
    private URLEntity[] descriptionURLEntities;
    private URLEntity urlEntity;
    private boolean isContributorsEnabled;
    private String profileImageUrl;
    private String profileImageUrlHttps;
    private String url;
    private boolean isProtected;
    private int followersCount;

    private Status status;

    private String profileBackgroundColor;
    private String profileTextColor;
    private String profileLinkColor;
    private String profileSidebarFillColor;
    private String profileSidebarBorderColor;
    private boolean profileUseBackgroundImage;
    private boolean showAllInlineMedia;
    private int friendsCount;
    private Date createdAt;
    private int favouritesCount;
    private int utcOffset;
    private String timeZone;
    private String profileBackgroundImageUrl;
    private String profileBackgroundImageUrlHttps;
    private String profileBannerImageUrl;
    private boolean profileBackgroundTiled;
    private String lang;
    private int statusesCount;
    private boolean isGeoEnabled;
    private boolean isVerified;
    private boolean translator;
    private int listedCount;
    private boolean isFollowRequestSent;
    
    private transient RateLimitStatus rateLimitStatus = null;
    private transient int accessLevel;

    private static final long serialVersionUID = -6345893237975349030L;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public URLEntity[] getDescriptionURLEntities() {
		return descriptionURLEntities;
	}

	public void setDescriptionURLEntities(URLEntity[] descriptionURLEntities) {
		this.descriptionURLEntities = descriptionURLEntities;
	}

	public void setURLEntity(URLEntity urlEntity) {
		this.urlEntity = urlEntity;
	}

	public boolean isContributorsEnabled() {
		return isContributorsEnabled;
	}

	public void setContributorsEnabled(boolean isContributorsEnabled) {
		this.isContributorsEnabled = isContributorsEnabled;
	}

	public void setProfileImageURL(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public URL getProfileImageUrlHttps() {
        try {
            return new URL(profileImageUrlHttps);
        } catch (MalformedURLException e) {
            return null;
        }
	}

	public void setProfileImageUrlHttps(String profileImageUrlHttps) {
		this.profileImageUrlHttps = profileImageUrlHttps;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getProfileBackgroundColor() {
		return profileBackgroundColor;
	}

	public void setProfileBackgroundColor(String profileBackgroundColor) {
		this.profileBackgroundColor = profileBackgroundColor;
	}

	public String getProfileTextColor() {
		return profileTextColor;
	}

	public void setProfileTextColor(String profileTextColor) {
		this.profileTextColor = profileTextColor;
	}

	public String getProfileLinkColor() {
		return profileLinkColor;
	}

	public void setProfileLinkColor(String profileLinkColor) {
		this.profileLinkColor = profileLinkColor;
	}

	public String getProfileSidebarFillColor() {
		return profileSidebarFillColor;
	}

	public void setProfileSidebarFillColor(String profileSidebarFillColor) {
		this.profileSidebarFillColor = profileSidebarFillColor;
	}

	public String getProfileSidebarBorderColor() {
		return profileSidebarBorderColor;
	}

	public void setProfileSidebarBorderColor(String profileSidebarBorderColor) {
		this.profileSidebarBorderColor = profileSidebarBorderColor;
	}

	public boolean isProfileUseBackgroundImage() {
		return profileUseBackgroundImage;
	}

	public void setProfileUseBackgroundImage(boolean profileUseBackgroundImage) {
		this.profileUseBackgroundImage = profileUseBackgroundImage;
	}

	public boolean isShowAllInlineMedia() {
		return showAllInlineMedia;
	}

	public void setShowAllInlineMedia(boolean showAllInlineMedia) {
		this.showAllInlineMedia = showAllInlineMedia;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getFavouritesCount() {
		return favouritesCount;
	}

	public void setFavouritesCount(int favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	public int getUtcOffset() {
		return utcOffset;
	}

	public void setUtcOffset(int utcOffset) {
		this.utcOffset = utcOffset;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getProfileBackgroundImageURL() {
		return profileBackgroundImageUrl;
	}

	public void setProfileBackgroundImageURL(String profileBackgroundImageUrl) {
		this.profileBackgroundImageUrl = profileBackgroundImageUrl;
	}

	public String getProfileBackgroundImageUrlHttps() {
		return profileBackgroundImageUrlHttps;
	}

	public void setProfileBackgroundImageUrlHttps(
			String profileBackgroundImageUrlHttps) {
		this.profileBackgroundImageUrlHttps = profileBackgroundImageUrlHttps;
	}

	public String getProfileBannerImageUrl() {
		return profileBannerImageUrl;
	}

	public void setProfileBannerImageUrl(String profileBannerImageUrl) {
		this.profileBannerImageUrl = profileBannerImageUrl;
	}

	public boolean isProfileBackgroundTiled() {
		return profileBackgroundTiled;
	}

	public void setProfileBackgroundTiled(boolean profileBackgroundTiled) {
		this.profileBackgroundTiled = profileBackgroundTiled;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public int getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(int statusesCount) {
		this.statusesCount = statusesCount;
	}

	public boolean isGeoEnabled() {
		return isGeoEnabled;
	}

	public void setGeoEnabled(boolean isGeoEnabled) {
		this.isGeoEnabled = isGeoEnabled;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public boolean isTranslator() {
		return translator;
	}

	public void setTranslator(boolean translator) {
		this.translator = translator;
	}

	public int getListedCount() {
		return listedCount;
	}

	public void setListedCount(int listedCount) {
		this.listedCount = listedCount;
	}

	public boolean isFollowRequestSent() {
		return isFollowRequestSent;
	}

	public void setFollowRequestSent(boolean isFollowRequestSent) {
		this.isFollowRequestSent = isFollowRequestSent;
	}

	public int compareTo(User o) {
		return (int) (this.id - o.getId());
	}

	public RateLimitStatus getRateLimitStatus() {
		return rateLimitStatus;
	}
	
	public void setRateLimitStatus(RateLimitStatus rateLimitStatus) {
		this.rateLimitStatus = rateLimitStatus;
	}

	public int getAccessLevel() {
		return accessLevel;
	}
	
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getProfileImageURL() {
		return profileImageUrl;
	}

	public String getBiggerProfileImageURL() {
		return profileImageUrl;
	}

	public String getMiniProfileImageURL() {
		return profileImageUrl;
	}

	public String getOriginalProfileImageURL() {
		return profileImageUrl;
	}

	public String getProfileImageURLHttps() {
		return profileImageUrlHttps;
	}

	public String getBiggerProfileImageURLHttps() {
		return profileImageUrlHttps;
	}

	public String getMiniProfileImageURLHttps() {
		return profileImageUrlHttps;
	}

	public String getOriginalProfileImageURLHttps() {
		return profileImageUrlHttps;
	}

	public String getURL() {
		return url;
	}

	public String getProfileBannerURL() {
		return profileBackgroundImageUrl;
	}

	public String getProfileBannerRetinaURL() {
		return profileBackgroundImageUrl;
	}

	public String getProfileBannerIPadURL() {
		return profileBackgroundImageUrl;
	}

	public String getProfileBannerIPadRetinaURL() {
		return profileBackgroundImageUrl;
	}

	public String getProfileBannerMobileURL() {
		return profileBackgroundImageUrl;
	}

	public String getProfileBannerMobileRetinaURL() {
		return profileBackgroundImageUrl;
	}

	public URLEntity getURLEntity() {
		return urlEntity;
	}

	public String getProfileBackgroundImageUrl() {
		return profileBackgroundImageUrl;
	}
}
