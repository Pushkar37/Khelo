const moongose=require("moongose");
const ConnectToDB=()=>{
 moongose.connect(process.env.Mongo_Uri).then(()=>{
    console.log("connection established");
})
}
const UserSchema=new moongose.Schema({

    email:{
        type:String,
        unique:true,
        trim:true,
        min:{11:"Invalid Email"}

    },
    password:{
        type:String,
        trim:true,
        min:{5:"invalid passowrd"}
    }
});
const Usermodel=moongose.model('user',UserSchema);

module.exports=Usermodel;
module.exports=ConnectToDB;
