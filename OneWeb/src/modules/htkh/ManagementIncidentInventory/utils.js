import {Queue} from './queue';

export class Utils {


    async getTreeNode(listItem) {

        if(listItem.length == 0) return;
        const node =  listItem[0]
        const id = node.donvi_cha_id

    
        const q = new Queue();
    
        let dist = []
    
        q.enqueue(id);
    
        while (!q.isEmpty()) {
          const item = q.dequeue();
    
          const subUser = listUsers.find((e) => e.id == item);
    
          if (subUser != null) {
            dist.push(subUser);
            q.enqueues(subUser.childs);
          }
        }
    
        const children = this.buildTreeNode(dist, id);
        return {
            id, 
          label: node.ten_dv,
          children,
        };
      }


     buildTreeNode(nodes, parent) {
        const tree = [];
        for (const node of nodes) {
          if (node.parent === parent) {
            const children = this.buildTreeNode(nodes, node.donvi_id);
            const treeNode = {
              id: node.donvi_id,
              label: node.ten_dv,
              children: children,
              // total_children: children.length,
            };
            tree.push(treeNode);
          }
        }
        console.log("tree", tree)
        return tree;
      }
}

export default new Utils();