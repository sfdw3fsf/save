import { ReviewDTO } from '@/store/post/dto/post.dto';
import { ICONS } from '@/theme';
import Image from 'next/image';
import { SortableContainer, SortableElement, SortableHandle } from 'react-sortable-hoc';
import ReviewItem from '../components/ReviewItem';

interface SortableGridProps {
  items: ReviewDTO[];
  shouldUseDragHandle: boolean;
  onSortEnd: ({ oldIndex, newIndex }: { oldIndex: number; newIndex: number }) => void;
}

interface SortableItemProps {
  item: ReviewDTO;
  order: number;
  shouldUseDragHandle: boolean;
  onDelete: (id: number) => void;
}

export const Handle = SortableHandle(({ tabIndex }: { tabIndex: number }) => (
  <div className='' tabIndex={tabIndex}>
    <Image src={ICONS.TWO_DRAG} alt='Drag' width={24} height={24} className='cursor-move' />
  </div>
));

const SortableItem = SortableElement<SortableItemProps>(({ item, order, shouldUseDragHandle, onDelete }: any) => {
  return (
    <ReviewItem
      key={item.id}
      id={item.id}
      index={order + 1}
      title={item.title}
      content={item.content}
      reviewerName={item.reviewerName}
      shouldUseDragHandle={shouldUseDragHandle}
      onDelete={onDelete}
    />
  );
});

export const SortableGrid = SortableContainer<SortableGridProps & { onDelete: (id: number) => void }>(
  ({ items, shouldUseDragHandle, onSortEnd, onDelete }: any) => {
    return (
      <div className='flex flex-wrap gap-5 md:gap-9 lg:gap-16'>
        {items.map((item: any, index: any) => {
          return (
            <SortableItem
              key={`item-${item.id}`}
              index={index}
              order={index}
              item={item}
              shouldUseDragHandle={shouldUseDragHandle}
              onDelete={onDelete}
            />
          );
        })}
      </div>
    );
  },
);
