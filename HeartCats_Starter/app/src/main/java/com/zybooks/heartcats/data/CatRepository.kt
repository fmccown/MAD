package com.zybooks.heartcats.data

class CatRepository() {
   fun getCats(): List<CatImage> = listOf(
      CatImage(
         id = "2bh",
         url = "https://cdn2.thecatapi.com/images/2bh.jpg",
         width = 500,
         height = 334
      ),
      CatImage(
         id = "34v",
         url = "https://cdn2.thecatapi.com/images/34v.jpg",
         width = 612,
         height = 612
      ),
      CatImage(
         id = "cou",
         url = "https://cdn2.thecatapi.com/images/cou.jpg",
         width = 512,
         height = 384
      ),
      CatImage(
         id = "bel",
         url = "https://cdn2.thecatapi.com/images/bel.jpg",
         width = 400,
         height = 266
      ),
      CatImage(
         id = "6je",
         url = "https://cdn2.thecatapi.com/images/6je.jpg",
         width = 500,
         height = 334
      ),
   )
}