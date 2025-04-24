const express=require("express");
const app=express();
const Userroutes=require('./routes/user.routes');
const Usermodel=require("./config/database");
const ConnectToDB=require("./config/database");
ConnectToDB();
dotenv.config();


app.listen(3030,()=>{
    console.log("Server started at 3030")
})
app.use("/user",Userroutes);
app.use(express.urlencoded({extended:true}));
app.use(express.json);
