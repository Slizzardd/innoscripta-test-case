import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { rootStore } from "../store/rootStore";
import { PAGES } from "../router/pages.enum";
import PageLayout from "../components/layout/PageLayout";
import styles from "./Auth.module.scss";

const Auth = () => {
  const [formState, setFormState] = useState({
    email: "",
    firstName: "",
    lastName: "",
    password: "",
    repeatPassword: "",
    errorText: "",
  });

  const [isLogin, setIsLogin] = useState(false);

  const navigate = useNavigate();
  const authStore = rootStore.authStore();

  const validate = () => {
    if (isLogin) {
      if (formState.email.length < 3 && formState.password.length < 3) {
        setFormState((prev) => ({
          ...prev,
          errorText: "Email and Password must contain more than 3 characters",
        }));
        return false;
      }
    } else {
      if (
        formState.email.length < 3 &&
        formState.firstName.length < 3 &&
        formState.lastName.length < 3 &&
        formState.password.length < 3
      ) {
        setFormState((prev) => ({
          ...prev,
          errorText:
            "Email, FirstName, LastName and Password must contain more than 3 characters",
        }));
        return false;
      }

      if (formState.password !== formState.repeatPassword) {
        setFormState((prev) => ({
          ...prev,
          errorText: "Passwords must match",
        }));
        return false;
      }
    }

    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$/;
    if (!emailPattern.test(formState.email)) {
      setFormState((prev) => ({ ...prev, errorText: "Wrong email" }));
      return false;
    }

    return true;
  };

  const submit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const isValid = validate();

    if (!isValid) return;

    const { email, firstName, lastName, password } = formState;

    if (isLogin) {
      authStore.login({ email, password }).then((token) => {
        if (token === null) {
          setFormState((prev) => ({
            ...prev,
            errorText: "Wrong email or password",
          }));
        } else {
          navigate(PAGES.Home);
        }
      });
    } else {
      authStore
        .register({ email, firstName, lastName, password })
        .then((token) => {
          if (token === null) {
            setFormState((prev) => ({
              ...prev,
              errorText: "Error in registration",
            }));
          } else {
            navigate(PAGES.Home);
          }
        });
    }
  };

  return (
    <PageLayout>
      <form onSubmit={submit} className={styles.form}>
        {!isLogin && (
          <>
            <input
              type="text"
              className={styles.form_input}
              value={formState.firstName}
              autoComplete={"First Name"}
              placeholder={"First Name"}
              onChange={(e) =>
                setFormState((prev) => ({
                  ...prev,
                  firstName: e.target.value,
                  errorText: "",
                }))
              }
            />
            <input
              type="text"
              className={styles.form_input}
              value={formState.lastName}
              autoComplete={"Last Name"}
              placeholder={"Last Name"}
              onChange={(e) =>
                setFormState((prev) => ({
                  ...prev,
                  lastName: e.target.value,
                  errorText: "",
                }))
              }
            />
          </>
        )}

        <input
          type="text"
          className={styles.form_input}
          value={formState.email}
          autoComplete={"Email"}
          placeholder={"Email"}
          onChange={(e) =>
            setFormState((prev) => ({
              ...prev,
              email: e.target.value,
              errorText: "",
            }))
          }
        />

        <input
          type="password"
          className={styles.form_input}
          value={formState.password}
          autoComplete={"new-password"}
          placeholder={"Password"}
          onChange={(e) =>
            setFormState((prev) => ({
              ...prev,
              password: e.target.value,
              errorText: "",
            }))
          }
        />

        {!isLogin && (
          <input
            type="password"
            className={styles.form_input}
            value={formState.repeatPassword}
            autoComplete={"repeat-new-password"}
            placeholder={"Repeat Password"}
            onChange={(e) =>
              setFormState((prev) => ({
                ...prev,
                repeatPassword: e.target.value,
                errorText: "",
              }))
            }
          />
        )}

        {formState.errorText !== "" && <p>{formState.errorText}</p>}

        <button className={styles.form_input_button}>{isLogin ? "sing in" : "sing up"}</button>

        <p className={styles.alreadyButton} onClick={() => setIsLogin((prev) => !prev)}>
          {isLogin ? "do not have account" : "already have account"}
        </p>
      </form>
    </PageLayout>
  );
};

export default Auth;
