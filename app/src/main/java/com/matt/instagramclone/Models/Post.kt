package com.matt.instagramclone.Models

class Post {

    private var postid: String = ""
    private var postimage: String = ""
    private var publisher: String = ""
    private var descriprion: String = ""

    constructor()

    constructor(postid: String, postimage: String, publisher: String, descriprion: String) {
        this.postid = postid
        this.postimage = postimage
        this.publisher = publisher
        this.descriprion = descriprion
    }

    fun getPostid(): String {
        return postid
    }

    fun setPostid(postid: String) {
        this.postid = postid
    }

    fun getPostimage(): String {
        return postimage
    }

    fun setPostimage(postimage: String) {
        this.postimage = postimage
    }

    fun getPublisher(): String {
        return publisher
    }

    fun setPublisher(publisher: String) {
        this.publisher = publisher
    }

    fun getDescription(): String {
        return descriprion
    }

    fun setDescription(descriprion: String) {
        this.descriprion = descriprion
    }

}