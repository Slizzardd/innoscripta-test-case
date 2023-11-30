import { create } from "zustand";
import { api } from "../utils/constants";

export interface News {
  title: string;
  description: string;
  sourcePublisher: string;
  content: string | null;
  sourceImage: null | string;
  publisher: string;
}

interface GetNewsBody {
  page?: number;
  size?: number;
  sortBy?: "publisher" | "publishedAt" | "title";
  orderBy?: "desc" | "asc";
  specifications?: string;
  myFavorite?: boolean;

  authToken: string | null;
}

interface I_newsStore {
  news: Array<News>;
  endNews: boolean;

  clearNews: () => void;
  getNews: (getNewsBody: GetNewsBody) => Promise<void | null>;
}

const newsStore = create<I_newsStore>()((set, get) => ({
  news: [],
  endNews: false,

  clearNews: () => {
    set((state) => ({ ...state, news: [] }));
  },

  getNews: async (getNewsBody) => {
    const url = new URL(api + "/news/findAll");
    url.searchParams.set("page", String(getNewsBody.page));
    // url.searchParams.set("size", String(getNewsBody.size));
    url.searchParams.set("sortBy", String(getNewsBody.sortBy));
    url.searchParams.set("orderBy", String(getNewsBody.orderBy));
    url.searchParams.set("specifications", String(getNewsBody.specifications));
    url.searchParams.set("myFavorite", String(getNewsBody.myFavorite));

    const headers = new Headers();
    if (getNewsBody.authToken !== null) {
      headers.set("Authorization", `Bearer ${getNewsBody.authToken}`);
    }

    const response = await fetch(url, { method: "GET", headers });
    const text = await response.text();

    const news: Array<News> = JSON.parse(text);
    if (news.length > 0) {
      set((store) => ({ ...store, news: [...store.news, ...news] }));
    } else {
      set((store) => ({ ...store, endNews: true }));
    }

    if (response.status === 401){
      return null;
    }
  },
}));

export default newsStore;
