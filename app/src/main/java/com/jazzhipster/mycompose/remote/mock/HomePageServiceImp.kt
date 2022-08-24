package com.jazzhipster.mycompose.remote.mock

import com.jazzhipster.mycompose.exception.ApiException
import com.jazzhipster.mycompose.remote.model.*
import com.jazzhipster.mycompose.remote.service.HomePageService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random
import kotlin.random.nextInt

class HomePageServiceImp() : HomePageService {
    override suspend fun login(request: LoginRequest): BaseModel<Unit> =
        withContext(Dispatchers.IO) {
            delay(500)
            if (request.account != "account" || request.password != "password")
                BaseModel(
                    status = 1,
                    errorMsg = "帳密不正確",
                    data = Unit
                )
            else
                BaseModel(
                    status = 0,
                    errorMsg = "",
                    data = Unit
                )

        }

    override suspend fun getBanner(): BaseModel<List<BannerBean>> =
        withContext(Dispatchers.IO) {
            delay(500)
            BaseModel(
                status = 0,
                errorMsg = "",
                data = List(10) {
                    BannerBean(
                        id = Random.nextInt(1000, 9999),
                        desc = "$it desc",
                        imagePath = "https://www.wanandroid.com/blogimgs/42da12d8-de56-4439-b40c-eab66c227a4b.png",
                        isVisible = 1,
                        title = "title $it",
                        order = 2,
                        type = 0,
                        url = "https://www.wanandroid.com/blog/show/3352"
                    )
                }
            )
        }

    override suspend fun getArticle(a: Int): BaseModel<ArticleListModel> =
        withContext(Dispatchers.IO) {
            delay(500)
            BaseModel(
                status = 0,
                errorMsg = "",
                data = ArticleListModel(
                    curPage = 5,
                    offset = 10,
                    over = false,
                    pageCount = 10,
                    size = 10,
                    total = 1000,
                    datas = List(a) {
                        ArticleModel(
                            apkLink = "https://developer.android.com/jetpack/compose/navigation?hl=zh-tw",
                            audit = 1,
                            author = "",
                            canEdit = false,
                            chapterId = 502,
                            chapterName = "自助",
                            collect = false,
                            courseId = 13,
                            desc = "",
                            descMd = "",
                            envelopePic = "https://www.wanandroid.com/blogimgs/42da12d8-de56-4439-b40c-eab66c227a4b.png",
                            fresh = true,
                            host = "",
                            id = 23807,
                            link = "https://juejin.cn/post/7127947917280673822",
                            niceDate = "47分鐘前",
                            niceShareDate = "47分鐘前",
                            origin = "",
                            prefix = "",
                            projectLink = "",
                            publishTime = 1659660010000,
                            realSuperChapterId = 493,
                            selfVisible = 0,
                            shareDate = 1659660010000,
                            shareUser = "equationl",
                            superChapterId = 494,
                            superChapterName = "廣場Tab",
                            tags = listOf(),
                            title = "compose",
                            type = 0,
                            userId = 87590,
                            visible = 1,
                            zan = 0
                        )
                    }

                )
            )
        }

    override suspend fun getProjectTree(): BaseModel<List<ProjectTreeModel>> =
        withContext(Dispatchers.IO) {
            BaseModel(
                status = 0,
                errorMsg = "",
                data = List(10) {
                    ProjectTreeModel(
                        author = "",
                        children = listOf(),
                        courseId = 13,
                        cover = "",
                        desc = "",
                        id = 294,
                        lisense = "",
                        lisenseLink = "",
                        name = "完整項目",
                        order = 145000,
                        parentChapterId = 293,
                        userControlSetTop = false,
                        visible = 0

                    )
                }
            )
        }

    override suspend fun getProject(page: Int, cid: Int): BaseModel<List<ArticleModel>> =
        withContext(Dispatchers.IO) {
            delay(500)
            BaseModel(
                status = 0,
                errorMsg = "",
                data = List(10) {
                    ArticleModel(
                        apkLink = "https://developer.android.com/jetpack/compose/navigation?hl=zh-tw",
                        audit = 1,
                        author = "Lowae",
                        canEdit = false,
                        chapterId = 294,
                        chapterName = "完整項目",
                        collect = false,
                        courseId = 13,
                        desc = "WanAndroid的更佳Material Design实践，严格遵循Material设计，完美支持其Dynamic Colors等新特性，MVVM架构，保证UI风格、逻辑设计的一致性。全盘采用Jetpack+协程，只为解决某些特定问题而引入其他依赖，避免大材小用，且尽可能均自己实现",
                        descMd = "",
                        envelopePic = "https://wanandroid.com/blogimgs/e1123cd4-d156-4378-85b3-51c10c838911.png",
                        fresh = false,
                        host = "",
                        id = 23423,
                        link = "https://www.wanandroid.com/blog/show/3387",
                        niceDate = "2022-07-10 13:03",
                        niceShareDate = "2022-07-10 13:03",
                        origin = "",
                        prefix = "",
                        projectLink = "https://github.com/Lowae/Design-WanAndroid",
                        publishTime = 1657429387000,
                        realSuperChapterId = 293,
                        selfVisible = 0,
                        shareDate = 1657429387000,
                        shareUser = "",
                        superChapterId = 294,
                        superChapterName = "開源項目主Tab",
                        tags = listOf(),
                        title = "\uD83E\uDD84Design WanAndroid（WanAndroid的更佳的可使用客户端）",
                        type = 0,
                        userId = -1,
                        visible = 1,
                        zan = 0

                    )
                }
            )
        }

}