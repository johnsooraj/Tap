package com.cyspan.tap.subscription.dao;

public interface FetchSubscriptionQuery {

	public static final String kFeedbackGroupTableName = " tap.SubscriptionFeedbackGroup sfeed ";
	public static final String kPollTableName = " tap.SubscriptionPoll spoll ";
	public static final String kNoticeTableName = " tap.SubscriptionNotice snote ";
	public static final String kClearFlagTableName = " tap.SubscriptionClearFlag scflag ";

	public static final String kSELECT = " SELECT ";
	public static final String kUNION = " UNION";
	public static final String kFROM = " FROM ";
	public static final String kLEFT_JOIN = " LEFT JOIN ";
	public static final String kWHERE = " WHERE ";
	public static final String kAND = " AND ";
	public static final String kLIKE = " LIKE ";

	public static final String kSelectAllBase = "SELECT * FROM ( ";
	public static final String kSelectCountBase = "SELECT count(*) FROM ( ";

	public static final String kSelectFeedbackBase = " SELECT sfeed.id AS fid, NULL AS pid, NULL AS nid, sfeed.createdate AS cd ";
	public static final String kSelectPollkBase = " SELECT NULL AS fid, spoll.pollid AS pid, NULL AS nid, spoll.createdate AS cd ";
	public static final String kSelectNoticeBase = " SELECT NULL AS fid, NULL AS pid, snote.noticeid AS nid, snote.createdate AS cd ";

	default String kSelectAllBaseEnd(boolean count, int start, int end) {
		if (count) {
			return " ) AS res ORDER BY res.cd DESC";
		} else {
			return " ) AS res ORDER BY res.cd DESC limit " + start + ", " + end;
		}
	}
}
