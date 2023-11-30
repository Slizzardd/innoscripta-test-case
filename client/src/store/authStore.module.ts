import { create } from "zustand";
import { persist, createJSONStorage } from "zustand/middleware";
import { api } from "../utils/constants";

interface RegisterLoginResponse {
  token: string;
  user: {
    id: string;
    email: string;
    firstName: string;
    lastName: string;
    preferredPublishers: string[];
  };
}

interface I_authStore {
  authToken: string | null;
  publishers: string[];

  user: {
    id: string;
    email: string;
    firstName: string;
    lastName: string;
    preferredPublishers: string[];
  } | null;

  register: (registerBody: {
    email: string;
    firstName: string;
    lastName: string;
    password: string;
  }) => Promise<string | null>;

  login: (loginBody: {
    email: string;
    password: string;
  }) => Promise<string | null>;

  logout: () => void;
  getPublishers: () => Promise<string[] | null>;

  changeUser: (changeUserBody: {
    firstName?: string;
    lastName?: string;
    email?: string;
    preferredPublishers?: string[];
  }) => Promise<void | null>;

  deleteUser: (token: string) => Promise<void>;

  getUser: (token: string) => Promise<string | null>;
}

const authStore = create<I_authStore>()(
  persist(
    (set, get) => ({
      authToken: null,
      user: null,
      publishers: [],

      register: async (registerBody) => {
        const response = await fetch(`${api}/auth/registration`, {
          method: "POST",
          body: JSON.stringify(registerBody),
          headers: { "content-type": "application/json" },
        });
        if (response.status !== 200) {
          return null;
        }

        const { token, user }: RegisterLoginResponse = JSON.parse(
          await response.text(),
        );
        set((store) => ({ ...store, authToken: token, user }));
        return token;
      },

      login: async (loginBody) => {
        try {
          const response = await fetch(`${api}/auth/login`, {
            method: "POST",
            body: JSON.stringify(loginBody),
            headers: { "content-type": "application/json" },
          });

          if (response.status !== 200) {
            return null;
          }

          const { token, user }: RegisterLoginResponse = JSON.parse(
            await response.text(),
          );

          set((store) => ({ ...store, authToken: token, user }));

          return token;
        } catch (e) {
          return null;
        }
      },

      logout: () => {
        set((store) => ({ ...store, authToken: null, user: null }));
      },

      getPublishers: async () => {
        try {
          const response = await fetch(`${api}/news/getAllPublisher`);

          if (response.status !== 200) {
            return null;
          }

          const publishers: string[] = JSON.parse(await response.text());

          set((state) => ({ ...state, publishers }));

          return publishers;
        } catch (e) {
          return null;
        }
      },

      getUser: async () => {
        try {
          const response = await fetch(`${api}/user/getUserByToken`, {
            method: "GET",
            headers: {
              Authorization: `Bearer ${get().authToken}`,
              "content-type": "application/json",
            },
          });

          if (response.status !== 200) {
            return null;
          }

          const { token, user }: RegisterLoginResponse = JSON.parse(
            await response.text(),
          );

          set((store) => ({ ...store, authToken: token, user }));

          return token;
        } catch (e) {
          return null;
        }
      },

      changeUser: async (changeUserBody) => {
        try {
          const requestBody: { [key: string]: any } = {};

          if (changeUserBody.firstName !== undefined) {
            requestBody["firstName"] = changeUserBody.firstName;
          }
          if (changeUserBody.lastName !== undefined) {
            requestBody["lastName"] = changeUserBody.lastName;
          }

          if (changeUserBody.email !== undefined) {
            requestBody["email"] = changeUserBody.email;
          }

          if (changeUserBody.preferredPublishers !== undefined) {
            requestBody["preferredPublishers"] =
              changeUserBody.preferredPublishers;
          }

          const response = await fetch(`${api}/user/updateUser`, {
            method: "POST",
            headers: {
              Authorization: `Bearer ${get().authToken}`,
              "content-type": "application/json",
            },
            body: JSON.stringify(requestBody),
          });
          if (response.status === 401) {
            set((store) => ({ ...store, user: null, authToken: null }));

            return null;
          }

          if (response.status !== 200) {
            return null;
          }

          return;
        } catch (e) {
          return null;
        }
      },

      deleteUser: async () => {
        set((store) => ({ ...store, authToken: null, user: null }));
        await fetch(`${api}/user/deleteUser`, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${get().authToken}`,
            "content-type": "application/json",
          },
        });
      },
    }),
    {
      storage: createJSONStorage(() => localStorage),
      name: "AUTH_STORE",
      partialize: (state) => ({
        authToken: state.authToken,
        user: state.user,
        publishers: state.publishers,
      }),
    },
  ),
);

export default authStore;
