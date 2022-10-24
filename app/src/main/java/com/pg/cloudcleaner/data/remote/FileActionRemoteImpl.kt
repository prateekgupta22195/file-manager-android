package com.pg.cloudcleaner.data.remote

import com.pg.cloudcleaner.network.retrofit
import com.pg.cloudcleaner.service.DriveAPIResponse
import com.pg.cloudcleaner.service.GoogleDriveApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class FileActionRemoteImpl(
    private val googleDriveApiService: GoogleDriveApiService = retrofit.create(GoogleDriveApiService::class.java),
    private val apiKey: String
) :
    FileActionRemote {

    override fun getFiles(accessToken: String, pageToken: String?): Call<DriveAPIResponse> {
        return googleDriveApiService.filesData(
            "Bearer $accessToken",
            fields = "nextPageToken,files(createdTime,modifiedTime,size,viewedByMeTime,id,mimeType,originalFilename,thumbnailLink)",
            pageSize = 500, pageToken = pageToken, key = apiKey
        )
    }

    override suspend fun deleteFile(accessToken: String, fileId: String): Response<ResponseBody> {
        return googleDriveApiService.deleteFile(fileId = fileId, auth = accessToken, key = apiKey)
    }
}
