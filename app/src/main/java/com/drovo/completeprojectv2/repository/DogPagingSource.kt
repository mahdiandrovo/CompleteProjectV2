package com.drovo.completeprojectv2.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.drovo.completeprojectv2.data.Dog
import com.drovo.completeprojectv2.network.ApiService
import retrofit2.HttpException
import java.io.IOException

class DogPagingSource constructor(
    private val apiService: ApiService
):PagingSource<Int, Dog>() {

    override fun getRefreshKey(state: PagingState<Int, Dog>): Int? {
        return null
    }

    //params: LoadParams<Int>
    //params jane ami ki type er data load korbo server theke
    //LoadResult<Int, Dog> eta ekta seale
    //ekhane 2 ta function ase Success r Failure
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dog> {
        val page = params.key ?: 1 //jodi key thake oita pass koro nahole 1 pass koro
        //data load time success or error ashte pare any time
        //tai try catch e felte hbe and LoadResult sealed class er help nite hbe
        return try {
            val response = apiService.getAllDogs(page, params.loadSize)
            //jodi load korte success ashe tahole...
            //LoadResult.Page 3 ta parameter dibe
            //response, prevKey = current page, nextKey = next page
            //paging dekhe ekhn kon page e asi r next e kon page er data load korte hbe

            //prevKey = if (page == 1) null else page-1
            //jodi page 1 e thako tahole tmr kono prev key nai nahole ekhn j page e aso ta the 1 minus koro

            //if (response.isEmpty()) null else page+1
            //jodi empty page ashe nahole null nahole ekhn j page e aso ta +1

            LoadResult.Page(
                response,
                prevKey = if (page == 1) null else page-1,
                nextKey = if (response.isEmpty()) null else page+1
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }

    }
}