package org.cnodejs.android.md.model.api

import org.cnodejs.android.md.model.entity.ApiResult
import org.cnodejs.android.md.model.entity.DataResult
import org.cnodejs.android.md.model.entity.LoginResult
import org.cnodejs.android.md.model.entity.MessageData
import org.cnodejs.android.md.model.entity.MessageMarkedResult
import org.cnodejs.android.md.model.entity.MessagesMarkedAllResult
import org.cnodejs.android.md.model.entity.ReplyIdResult
import org.cnodejs.android.md.model.entity.Topic
import org.cnodejs.android.md.model.entity.TopicDetail
import org.cnodejs.android.md.model.entity.TopicIdResult
import org.cnodejs.android.md.model.entity.UpReplyResult
import org.cnodejs.android.md.model.entity.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CNodeApi {
    companion object {
        const val PAGE_FIRST = 1
        const val PAGE_LIMIT = 20
        const val MD_RENDER = false
    }

    @GET("topics")
    suspend fun getTopics(
        @Query("tab") tab: String? = null,
        @Query("page") page: Int = PAGE_FIRST,
        @Query("limit") limit: Int = PAGE_LIMIT,
        @Query("mdrender") mdrender: Boolean = MD_RENDER,
    ): DataResult<List<Topic>>

    @GET("topic/{topicId}")
    suspend fun getTopic(
        @Path("topicId") topicId: String,
        @Query("accesstoken") accessToken: String? = null,
        @Query("mdrender") mdrender: Boolean = MD_RENDER,
    ): DataResult<TopicDetail>

    @POST("topics")
    @FormUrlEncoded
    suspend fun createTopic(
        @Field("accesstoken") accessToken: String,
        @Field("title") title: String,
        @Field("tab") tab: String,
        @Field("content") content: String,
    ): TopicIdResult

    @POST("topics/update")
    @FormUrlEncoded
    suspend fun updateTopic(
        @Field("accesstoken") accessToken: String,
        @Field("topic_id") topicId: String,
        @Field("title") title: String,
        @Field("tab") tab: String,
        @Field("content") content: String,
    ): TopicIdResult

    @POST("topic_collect/collect")
    @FormUrlEncoded
    suspend fun collectTopic(
        @Field("accesstoken") accessToken: String,
        @Field("topic_id") topicId: String,
    ): ApiResult

    @POST("topic_collect/de_collect")
    @FormUrlEncoded
    suspend fun decollectTopic(
        @Field("accesstoken") accessToken: String,
        @Field("topic_id") topicId: String,
    ): ApiResult

    @GET("topic_collect/{loginName}")
    suspend fun getUserCollectTopics(
        @Path("loginName") loginName: String,
    ): DataResult<List<Topic>>

    @POST("topic/{topicId}/replies")
    @FormUrlEncoded
    suspend fun createReply(
        @Path("topicId") topicId: String,
        @Field("accesstoken") accessToken: String,
        @Field("content") content: String,
        @Field("reply_id") toReplyId: String?,
    ): ReplyIdResult

    @POST("reply/{replyId}/ups")
    @FormUrlEncoded
    suspend fun upReply(
        @Path("replyId") replyId: String,
        @Field("accesstoken") accessToken: String,
    ): UpReplyResult

    @GET("user/{loginName}")
    suspend fun getUser(
        @Path("loginName") loginName: String,
    ): DataResult<User>

    @POST("accesstoken")
    @FormUrlEncoded
    suspend fun login(
        @Field("accesstoken") accessToken: String,
    ): LoginResult

    @GET("message/count")
    suspend fun getMessagesCount(
        @Query("accesstoken") accessToken: String,
    ): DataResult<Int>

    @GET("messages")
    suspend fun getMessages(
        @Query("accesstoken") accessToken: String,
        @Query("mdrender") mdrender: Boolean = MD_RENDER,
    ): DataResult<MessageData>

    @POST("message/mark_all")
    @FormUrlEncoded
    suspend fun markAllMessagesRead(
        @Field("accesstoken") accessToken: String,
    ): MessagesMarkedAllResult

    @POST("message/mark_one/{messageId}")
    @FormUrlEncoded
    suspend fun markMessageRead(
        @Path("messageId") messageId: String,
        @Field("accesstoken") accessToken: String,
    ): MessageMarkedResult
}
