import React from "react";
import {rootStore} from "../store/rootStore";

import styles from "./Filter.module.scss";

export interface I_Filter {
    sortBy: "publisher" | "publishedAt" | "title";
    orderBy: "desc" | "asc";
    specifications: string;
    myFavorite: boolean;
}

interface FilterProps {
    filter: I_Filter;
    setFilter: React.Dispatch<React.SetStateAction<I_Filter>>;
}

const Filter: React.FC<FilterProps> = (props) => {
    const {filter, setFilter} = props;

    const token = rootStore.authStore().authToken;

    return (
        <div className={styles.filter}>
            <input
                className={styles.filter_search}
                placeholder={"Search"}
                value={filter.specifications}
                onChange={(e) =>
                    setFilter((prev) => ({...prev, specifications: e.target.value}))
                }
            />

            <div className={styles.filter_info}>
                <p>Search by:</p>
                <select
                    className={styles.filter_info_selecter}
                    onChange={(e) =>
                        setFilter((prev) => ({
                            ...prev,
                            sortBy: e.target.value as "publisher" | "publishedAt" | "title",
                        }))
                    }
                >
                    {["title", "publishedAt", "publisher"].map((item, index) => (
                        <option key={index}>{item}</option>
                    ))}
                </select>

                <p>Order by:</p>
                <select
                    className={styles.filter_info_selecter}
                    onChange={(e) =>
                        setFilter((prev) => ({
                            ...prev,
                            orderBy: e.target.value as "asc" | "desc",
                        }))
                    }
                >
                    {["desc", "asc"].map((item, index) => (
                        <option key={index}>{item}</option>
                    ))}
                </select>

                {token !== null && (
                    <label className={styles.filter_info_selecter}>
                        <input
                            className={styles.filter_info_checkbox}
                            type={"checkbox"}
                            onChange={(e) =>
                                setFilter((prev) => ({...prev, myFavorite: e.target.checked}))
                            }
                        />
                        <p>myFavorite</p>
                    </label>
                )}
            </div>
        </div>
    );
};

export default Filter;
