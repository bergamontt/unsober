import { useState, useEffect } from 'react';

interface Response<T> {
    data: T | null;
    loading: boolean;
    error: string | null;
}

function useFetch<T, Args extends unknown[]>(
    fetchFunction: (...args: Args) => Promise<T>,
    args: Args
) : Response<T> {
    const [data, setData] = useState<T | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        setData(null);
        setLoading(true);
        setError(null);

        fetchFunction(...args)
            .then(result => setData(result))
            .catch(error => setError(error))
            .finally(() => setLoading(false));
    }, [fetchFunction, ...args]);
    return { data, loading, error };
}

export default useFetch