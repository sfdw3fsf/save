import ViewToggleItem, { ViewToggleItemProps } from './ViewToggleItem';

type ViewToggleProps = { items: ViewToggleItemProps[] };

const ViewToggle = ({ items }: ViewToggleProps): JSX.Element => {
  return (
    <div className='flex w-full items-center justify-center rounded-xl bg-weak-100 p-1 gap-1 overflow-hidden'>
      {items.map((item, index) => (
        <ViewToggleItem key={index} {...item} />
      ))}
    </div>
  );
};

export default ViewToggle;
