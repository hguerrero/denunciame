package com.finders.rules;

import java.util.Date;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

public class MockStatus implements Status {

	private static final long serialVersionUID = -7284708239736552059L;
	
    private Date createdAt;
    private long id;
    private String text;
    private String source;
    private boolean isTruncated;
    private long inReplyToStatusId;
    private long inReplyToUserId;
    private boolean isFavorited;
    private boolean isRetweeted;
    private int favoriteCount;
    private String inReplyToScreenName;
    private GeoLocation geoLocation = null;
    private Place place = null;
    // this field should be int in theory, but left as long for the serialized form compatibility - TFJ-790
    private long retweetCount;
    private boolean isPossiblySensitive;
    private String isoLanguageCode;

    private long[] contributorsIDs;

    private Status retweetedStatus;
    private UserMentionEntity[] userMentionEntities;
    private URLEntity[] urlEntities;
    private HashtagEntity[] hashtagEntities;
    private MediaEntity[] mediaEntities;
    private SymbolEntity[] symbolEntities;
    
    private long currentUserRetweetId = -1L;

    private transient RateLimitStatus rateLimitStatus = null;
    private transient int accessLevel;

    private User user = null;

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isTruncated() {
		return isTruncated;
	}

	public void setTruncated(boolean isTruncated) {
		this.isTruncated = isTruncated;
	}

	public long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public void setInReplyToStatusId(long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	public long getInReplyToUserId() {
		return inReplyToUserId;
	}

	public void setInReplyToUserId(long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	public boolean isFavorited() {
		return isFavorited;
	}

	public void setFavorited(boolean isFavorited) {
		this.isFavorited = isFavorited;
	}

	public boolean isRetweeted() {
		return isRetweeted;
	}

	public void setRetweeted(boolean isRetweeted) {
		this.isRetweeted = isRetweeted;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}

	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}

	public GeoLocation getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public int getRetweetCount() {
		return (int) retweetCount;
	}

	public void setRetweetCount(long retweetCount) {
		this.retweetCount = retweetCount;
	}

	public boolean isPossiblySensitive() {
		return isPossiblySensitive;
	}

	public void setPossiblySensitive(boolean isPossiblySensitive) {
		this.isPossiblySensitive = isPossiblySensitive;
	}

	public String getIsoLanguageCode() {
		return isoLanguageCode;
	}

	public void setIsoLanguageCode(String isoLanguageCode) {
		this.isoLanguageCode = isoLanguageCode;
	}

	public void setContributors(long[] contributorsIDs) {
		this.contributorsIDs = contributorsIDs;
	}

	public Status getRetweetedStatus() {
		return retweetedStatus;
	}

	public void setRetweetedStatus(Status retweetedStatus) {
		this.retweetedStatus = retweetedStatus;
	}

	public UserMentionEntity[] getUserMentionEntities() {
		return userMentionEntities;
	}

	public void setUserMentionEntities(UserMentionEntity[] userMentionEntities) {
		this.userMentionEntities = userMentionEntities;
	}

	public HashtagEntity[] getHashtagEntities() {
		return hashtagEntities;
	}

	public void setHashtagEntities(HashtagEntity[] hashtagEntities) {
		this.hashtagEntities = hashtagEntities;
	}

	public MediaEntity[] getMediaEntities() {
		return mediaEntities;
	}

	public void setMediaEntities(MediaEntity[] mediaEntities) {
		this.mediaEntities = mediaEntities;
	}

	public SymbolEntity[] getSymbolEntities() {
		return symbolEntities;
	}

	public void setSymbolEntities(SymbolEntity[] symbolEntities) {
		this.symbolEntities = symbolEntities;
	}

	public long getCurrentUserRetweetId() {
		return currentUserRetweetId;
	}

	public void setCurrentUserRetweetId(long currentUserRetweetId) {
		this.currentUserRetweetId = currentUserRetweetId;
	}

	public int compareTo(Status that) {
        long delta = this.id - that.getId();
        if (delta < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        } else if (delta > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) delta;
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

	public URLEntity[] getURLEntities() {
		return urlEntities;
	}

	public void setURLEntities(URLEntity[] urlEntities) {
		this.urlEntities = urlEntities;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public boolean isRetweet() {
		return retweetedStatus != null;
	}

	public long[] getContributors() {
		return contributorsIDs;
	}

	public boolean isRetweetedByMe() {
		return currentUserRetweetId != 1L;
	}
}
