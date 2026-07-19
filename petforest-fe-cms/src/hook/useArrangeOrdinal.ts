import { arrangeOrdinalApi } from '@/store/common/common.api';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { ArrangeOrdinal } from '@/utils/types/arrange-ordinal.type';
import { QueryKey, useMutation, useQueryClient } from '@tanstack/react-query';
import { AxiosError } from 'axios';

type useArrangeOrdinalProps = {
  url: string;
  queryKey: QueryKey;
  onError?: () => void;
  type?: any;
};

export const useArrangeOrdinal = ({ url, queryKey, type, onError }: useArrangeOrdinalProps) => {
  const queryClient = useQueryClient();

  const { mutate: mutateUpdateOrdinal } = useMutation({
    mutationFn: (body: ArrangeOrdinal) => arrangeOrdinalApi(url, { ...body, ...type }),
    onError: (error: AxiosError) => {
      handleError(error);
      onError && onError();
      queryClient.invalidateQueries({ queryKey: queryKey });
    },
  });

  const arrangeOrdinal = (array: any[], oldIndex: number, newIndex: number) => {
    const updatedArray = [...array];

    const [movedItem] = updatedArray.splice(oldIndex, 1);

    updatedArray.splice(newIndex, 0, movedItem);

    const affectedItems = updatedArray.slice(0, Math.max(oldIndex, newIndex) + 1);

    const result = affectedItems.map((item, index) => ({
      id: item.id,
      ordinal: index + 1,
    }));

    mutateUpdateOrdinal({ items: result });
  };

  return { arrangeOrdinal };
};

export default useArrangeOrdinalProps;
