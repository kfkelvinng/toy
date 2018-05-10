
```
from networkx.generators.social import karate_club_graph
import dash_core_components as dcc
import dash_html_components as html
import logging
from plotly.graph_objs import *
import dash
import networkx as nx

app = dash.Dash()

logging.basicConfig()

def nx_fig():
    G = karate_club_graph()

    # retain ordered list
    g_nodes = list(G.nodes())

    # Undirected graph
    pos = nx.spring_layout(G.to_undirected(), dim=3)
    nx.set_node_attributes(G, 'pos', pos)

    Xn = [pos[k][0] for k in g_nodes]  # x-coordinates of nodes
    Yn = [pos[k][1] for k in g_nodes]  # y-coordinates
    Zn = [pos[k][2] for k in g_nodes]  # z-coordinates
    Xe = []
    Ye = []
    Ze = []
    for n in g_nodes:
        for edge in G[n]:
            Xe += [pos[n][0], pos[edge][0], None]  # x-coordinates of edge ends
            Ye += [pos[n][1], pos[edge][1], None]
            Ze += [pos[n][2], pos[edge][2], None]
    labels = []
    group = []
    for node in g_nodes:
        labels.append(node)
        group.append(1 if G.node[node]['club'] == 'Mr. Hi' else 0)

    trace1 = Scatter3d(x=Xe,
                       y=Ye,
                       z=Ze,
                       mode='lines',
                       line=Line(color='rgb(125,125,125)', width=1),
                       hoverinfo='none'
                       )
    trace2 = Scatter3d(x=Xn,
                       y=Yn,
                       z=Zn,
                       mode='markers+text',
                       name='actors',
                       marker=Marker(symbol='dot',
                                     size=6,
                                     color=group,
                                     colorscale='Viridis',
                                     line=Line(color='rgb(50,50,50)', width=0.5)
                                     ),
                       text=labels,
                       hoverinfo='text'
                       )

    axis = dict(showbackground=False,
                showline=False,
                zeroline=False,
                showgrid=False,
                showticklabels=False,
                title=''
                )

    layout = Layout(
        title="",
        width=1000,
        height=1000,
        showlegend=False,
        scene=Scene(
            xaxis=XAxis(axis),
            yaxis=YAxis(axis),
            zaxis=ZAxis(axis),
        ),
        margin=Margin(
            t=0
        ),
        hovermode='closest',
        annotations=Annotations([
            Annotation(
                showarrow=False,
                text="Networkx Karate club",
                xref='paper',
                yref='paper',
                x=0,
                y=0.1,
                xanchor='left',
                yanchor='bottom',
                font=Font(
                    size=14
                )
            )
        ]), )

    data = Data([trace1, trace2])
    fig = Figure(data=data, layout=layout)
    return fig

app.layout = html.Div(
        children=[
            dcc.Graph(
                id='example-graph-2',
                figure=nx_fig()
            )
        ]
    )

app.run_server(host='0.0.0.0', port=8050)

```
