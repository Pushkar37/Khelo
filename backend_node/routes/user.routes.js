const express=require('express');
const Router=express.Router();
const {body,validationResult}=require('express-validator');
const bcrypt=require('bcrypt');
const Usermodel=require("./config/dabase.js");
Router.post("/register",body('email').trim().isEmail().isLength({min:13}),
body('password').trim().isLength({min:5}),(req,res)=>{
    if()
})



Router.post("/login",body('email').trim().isEmail()
,body('password').trim(),(req,res)=>{
   
})








module.exports=Router;
