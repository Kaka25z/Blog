import { SetStateAction, useState } from "react";
import "./login.css";
import SignInForm from "./Singin";
import SignUpForm from "./SignUp";
import { Button } from "antd";

const Login = () => {
    const [type, setType] = useState("signIn");
    const handleOnClick = (text: SetStateAction<string>) => {
      if (text !== type) {
        setType(text);
        return;
      }
    };

    const containerClass =
    "container " + (type === "signUp" ? "right-panel-active" : "");

    return (
        <div className="Login">
          <div className={containerClass} id="container">
            <SignUpForm />
            <SignInForm />
            <div className="overlay-container">
              <div className="overlay">
                <div className="overlay-panel overlay-left">
                  <Button
                    ghost
                    className="btn-grad"
                    id="signIn"
                    onClick={() => handleOnClick("signIn")}
                  >
                    去登录
                  </Button>
                </div>
                <div className="overlay-panel overlay-right">
                  <Button
                    ghost
                    className="btn-grad"
                    id="signUp"
                    onClick={() => handleOnClick("signUp")}
                  >
                    去注册
                  </Button>
                </div>
              </div>
            </div>
          </div>
        </div>
      );
}

export default Login;